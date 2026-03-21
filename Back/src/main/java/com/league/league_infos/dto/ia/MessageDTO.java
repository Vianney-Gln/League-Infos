package com.league.league_infos.dto.ia;

public class MessageDTO {
    private String role;
    private String content;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class Builder {
        private String role;
        private String content;

        public MessageDTO.Builder role(String role) {
            this.role = role;
            return this;
        }

        public MessageDTO.Builder content(String content) {
            this.content = content;
            return this;
        }

        public MessageDTO build() {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setRole(role);
            messageDTO.setContent(content);
            return messageDTO;
        }
    }
}
