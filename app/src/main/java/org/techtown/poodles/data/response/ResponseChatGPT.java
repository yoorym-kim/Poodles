package org.techtown.poodles.data.response;

import com.google.gson.annotations.SerializedName;

import org.techtown.poodles.data.request.MessageModel;

import java.util.List;

public class ResponseChatGPT {
    List<Choice> choices;
    Long created;
    String id;
    String model;
    String object;
    Usage usage;

    public ResponseChatGPT(List<Choice> choices, Long created, String id, String model, String object, Usage usage) {
        this.choices = choices;
        this.created = created;
        this.id = id;
        this.model = model;
        this.object = object;
        this.usage = usage;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }


    public class Choice {
        @SerializedName("finish_reason")
        String finishReason;
        Integer index;
        MessageModel message;

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public MessageModel getMessage() {
            return message;
        }

        public void setMessage(MessageModel message) {
            this.message = message;
        }
    }

    public class Usage {
        @SerializedName("completion_tokens")
        Integer completionTokens;
        @SerializedName("prompt_tokens")
        Integer promptTokens;
        @SerializedName("total_tokens")
        Integer totalTokens;
    }
}
