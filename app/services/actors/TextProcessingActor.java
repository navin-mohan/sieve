package services.actors;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.Props;
import services.utils.WordFrequencyCalculator;


public class TextProcessingActor extends AbstractActor {
    public final ActorRef top3MostFrequentWordsActor;
    public final ActorRef top3LeastFrequentWordsActor;
    public TextProcessingActor() {
        top3MostFrequentWordsActor = context().actorOf(Top3MostFrequentWordsActor.getProps(), "topKFrequentWordsActor");
        top3LeastFrequentWordsActor = context().actorOf(Top3LeastFrequentWordsActor.getProps(), "topKLeastFrequentWordsActor");
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

                    top3MostFrequentWordsActor.tell(new Top3MostFrequentWordsActorProtocol.AddNewEntry(wordFrequencyCalculator.getWordFrequency()), getSelf());
                    top3LeastFrequentWordsActor.tell(new Top3LeastFrequentWordsActorProtocol.AddNewEntry(wordFrequencyCalculator.getWordFrequency()), getSelf());
                })
                .match(Top3MostFrequentWordsActorProtocol.GetWords.class, getWords -> {
                    top3MostFrequentWordsActor.forward(getWords, getContext());
                })
                .match(Top3LeastFrequentWordsActorProtocol.GetWords.class, getWords -> {
                    top3LeastFrequentWordsActor.forward(getWords, getContext());
                })
                .build();
    }
}
