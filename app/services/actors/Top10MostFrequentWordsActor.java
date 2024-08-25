package services.actors;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.Props;
import services.utils.TopKFrequentItems;

public class Top10MostFrequentWordsActor extends AbstractActor {

    final TopKFrequentItems<String> topKFrequentItems = new TopKFrequentItems<>(10);
    public static Props getProps() {
        return Props.create(Top10MostFrequentWordsActor.class);
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Top10MostFrequentWordsActorProtocol.AddNewEntry.class, addNewEntry -> {
                    addNewEntry.getWordFrequency().forEach((word, frequency) -> topKFrequentItems.add(word, frequency));
                    System.out.println(topKFrequentItems.getTopK());
                })
                .match(Top10MostFrequentWordsActorProtocol.GetWords.class, getWords -> {
                    getSender().tell(topKFrequentItems.getTopK(), getSelf());
                })
                .build();
    }
}
