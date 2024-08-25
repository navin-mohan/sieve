package services.actors;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.Props;
import services.utils.TopKFrequentItems;

public class Top3MostFrequentWordsActor extends AbstractActor {

    final TopKFrequentItems<String> topKFrequentItems = new TopKFrequentItems<>(3);
    public static Props getProps() {
        return Props.create(Top3MostFrequentWordsActor.class);
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Top3MostFrequentWordsActorProtocol.AddNewEntry.class, addNewEntry -> {
                    addNewEntry.getWordFrequency().forEach((word, frequency) -> topKFrequentItems.add(word, frequency));
                })
                .match(Top3MostFrequentWordsActorProtocol.GetWords.class, getWords -> {
                    getSender().tell(topKFrequentItems.getTopK(), getSelf());
                })
                .build();
    }
}
