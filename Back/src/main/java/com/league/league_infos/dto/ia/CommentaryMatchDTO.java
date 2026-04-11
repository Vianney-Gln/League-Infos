package com.league.league_infos.dto.ia;


public class CommentaryMatchDTO extends OpenAiResponseDTO {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class Builder {
        private String id;
        private String object;
        private Long created;
        private String model;
        private String content;
        private String fingerPrint;
        private String serviceTier;

        public CommentaryMatchDTO.Builder id(String id) {
            this.id = id;
            return this;
        }

        public CommentaryMatchDTO.Builder object(String object) {
            this.object = object;
            return this;
        }

        public CommentaryMatchDTO.Builder created(Long created) {
            this.created = created;
            return this;
        }

        public CommentaryMatchDTO.Builder model(String model) {
            this.model = model;
            return this;
        }

        public CommentaryMatchDTO.Builder content(String content) {
            this.content = content;
            return this;
        }

        public CommentaryMatchDTO.Builder fingerPrint(String fingerPrint) {
            this.fingerPrint = fingerPrint;
            return this;
        }

        public CommentaryMatchDTO.Builder serviceTier(String serviceTier) {
            this.serviceTier = serviceTier;
            return this;
        }


        public CommentaryMatchDTO build() {
            CommentaryMatchDTO commentaryMatchDTO = new CommentaryMatchDTO();
            commentaryMatchDTO.setId(id);
            commentaryMatchDTO.setObject(object);
            commentaryMatchDTO.setCreated(created);
            commentaryMatchDTO.setModel(model);
            commentaryMatchDTO.setContent(content);
            commentaryMatchDTO.setServiceTier(serviceTier);
            commentaryMatchDTO.setFingerPrint(fingerPrint);
            return commentaryMatchDTO;
        }
    }
}
