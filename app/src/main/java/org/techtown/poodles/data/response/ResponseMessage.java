package org.techtown.poodles.data.response;

import java.util.ArrayList;

public class ResponseMessage {
    ArrayList<IdeaModel> ideas;

    public ResponseMessage(ArrayList<IdeaModel> ideas) {
        this.ideas = ideas;
    }

    public ArrayList<IdeaModel> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<IdeaModel> ideas) {
        this.ideas = ideas;
    }

    public class IdeaModel {
        String title;
        String description;

        public IdeaModel(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
