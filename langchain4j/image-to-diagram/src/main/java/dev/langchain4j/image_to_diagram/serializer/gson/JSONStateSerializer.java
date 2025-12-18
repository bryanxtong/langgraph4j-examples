package dev.langchain4j.image_to_diagram.serializer.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dev.langchain4j.image_to_diagram.ImageToDiagramWorkflow;
import dev.langchain4j.image_to_diagram.ImageToDiagram;
import dev.langchain4j.image_to_diagram.state.Diagram;
import net.sourceforge.plantuml.ErrorUmlType;
import org.bsc.langgraph4j.serializer.plain_text.PlainTextStateSerializer;
import org.bsc.langgraph4j.state.AgentStateFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONStateSerializer extends PlainTextStateSerializer<ImageToDiagram.State> {

    private static final Gson GSON = new GsonBuilder().create();

    public JSONStateSerializer(AgentStateFactory<ImageToDiagram.State> stateFactory) {
        super(stateFactory);
    }

    @Override
    public String writeDataAsString(Map<String, Object> data) {
        return GSON.toJson(data);
    }

    @Override
    public Map<String, Object> readDataFromString(String string) throws IOException {

        var root = JsonParser.parseString(string).getAsJsonObject();
        Map<String, Object> data = new HashMap<>();

        if (root.has("imageData")) {
            var val = GSON.fromJson(root.get("imageData"), ImageToDiagramWorkflow.ImageUrlOrData.class);
            if (val != null) data.put("imageData", val);
        }

        if (root.has("diagram")) {
            var val = GSON.fromJson(root.get("diagram"), Diagram.Element.class);
            if (val != null) data.put("diagram", val);
        }

        if (root.has("diagramCode")) {
            var val = GSON.fromJson(root.get("diagramCode"), new TypeToken<List<String>>() {
            }.getType());
            if (val != null) data.put("diagramCode", val);
        }

        if (root.has("evaluationResult")) {
            var val = GSON.fromJson(root.get("evaluationResult"), ImageToDiagram.EvaluationResult.class);
            if (val != null) data.put("evaluationResult", val);
        }

        if (root.has("evaluationError")) {
            var val = GSON.fromJson(root.get("evaluationError"), String.class);
            if (val != null) data.put("evaluationError", val);
        }

        if (root.has("evaluationErrorType")) {
            var val = GSON.fromJson(root.get("evaluationErrorType"), ErrorUmlType.class);
            if (val != null) data.put("evaluationErrorType", val);
        }
        return data;
    }
}