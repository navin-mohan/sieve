package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.util.FutureConverters;
import play.libs.Json;
import play.mvc.*;
import static org.apache.pekko.pattern.Patterns.ask;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    final ActorRef textProcessingActor;

    @Inject
    public HomeController(ActorSystem system) {
        this.textProcessingActor = system.actorOf(services.actors.TextProcessingActor.getProps(), "textProcessingActor");
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }


    @BodyParser.Of(BodyParser.Json.class)
    public Result ingest(Http.Request request) {
        Http.RequestBody body = request.body();
        final String text = body.asJson().get("text").asText();
        final boolean isParallel = body.asJson().get("parallel").asBoolean();

        textProcessingActor.tell(new services.actors.TextProcessingActorProtocol.GetWordFrequency(text, isParallel), ActorRef.noSender());

        return ok();
    }

    public CompletionStage<Result> get10MostFrequentWords() {
        return FutureConverters.asJava(
                ask(textProcessingActor, new services.actors.Top10MostFrequentWordsActorProtocol.GetWords(), 1000))
                .thenApply(response -> {
                    ObjectNode json = Json.newObject();
                    json.put("words", Json.toJson(response));
                    return ok(json);
                });
    }

    public CompletionStage<Result> get10LeastFrequentWords() {
        return FutureConverters.asJava(
                ask(textProcessingActor, new services.actors.Top10LeastFrequentWordsActorProtocol.GetWords(), 1000))
                .thenApply(response -> {
                            ObjectNode json = Json.newObject();
                            json.put("words", Json.toJson(response));
                            return ok(json);
                        });
    }

}
