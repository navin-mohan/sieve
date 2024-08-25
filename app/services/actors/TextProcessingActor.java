package services.actors;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.Props;
import services.utils.WordFrequencyCalculator;


public class TextProcessingActor extends AbstractActor {
    public final ActorRef topKMostFrequentWordsActor;
    public final ActorRef topKLeastFrequentWordsActor;
    public TextProcessingActor() {
        topKMostFrequentWordsActor = context().actorOf(Top10MostFrequentWordsActor.getProps(), "topKFrequentWordsActor");
        topKLeastFrequentWordsActor = context().actorOf(Top10LeastFrequentWordsActor.getProps(), "topKLeastFrequentWordsActor");
    }

    public static Props getProps() {
        return Props.create(TextProcessingActor.class);
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TextProcessingActorProtocol.GetWordFrequency.class, getWordFrequency -> {
                    WordFrequencyCalculator wordFrequencyCalculator = getWordFrequency.isParallel() ? new services.utils.ParallelWordFrequencyCalculator() : new services.utils.SerialWordFrequencyCalculator();

                    wordFrequencyCalculator.addString(getWordFrequency.getText());

                    wordFrequencyCalculator.getWordFrequency();

                    topKMostFrequentWordsActor.tell(new Top10MostFrequentWordsActorProtocol.AddNewEntry(wordFrequencyCalculator.getWordFrequency()), getSelf());
                    topKLeastFrequentWordsActor.tell(new Top10LeastFrequentWordsActorProtocol.AddNewEntry(wordFrequencyCalculator.getWordFrequency()), getSelf());
                })
                .match(Top10MostFrequentWordsActorProtocol.GetWords.class, getWords -> {
                    topKMostFrequentWordsActor.forward(getWords, getContext());
                })
                .match(Top10LeastFrequentWordsActorProtocol.GetWords.class, getWords -> {
                    topKLeastFrequentWordsActor.forward(getWords, getContext());
                })
                .build();
    }
}
