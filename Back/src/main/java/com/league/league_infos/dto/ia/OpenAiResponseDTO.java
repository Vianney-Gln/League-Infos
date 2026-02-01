package com.league.league_infos.dto.ia;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenAiResponseDTO {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<ChoiceDTO> choices;

    @JsonProperty("service_tier")
    private String serviceTier;

    @JsonProperty("system_fingerprint")
    private String fingerPrint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChoiceDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceDTO> choices) {
        this.choices = choices;
    }

    public String getServiceTier() {
        return serviceTier;
    }

    public void setServiceTier(String serviceTier) {
        this.serviceTier = serviceTier;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public static class Builder {
        private String id;
        private String object;
        private Long created;
        private String model;
        private List<ChoiceDTO> choices;
        private String serviceTier;
        private String fingerPrint;

        public OpenAiResponseDTO.Builder id(String id) {
            this.id = id;
            return this;
        }

        public OpenAiResponseDTO.Builder object(String object) {
            this.object = object;
            return this;
        }

        public OpenAiResponseDTO.Builder created(Long created) {
            this.created = created;
            return this;
        }

        public OpenAiResponseDTO.Builder model(String model) {
            this.model = model;
            return this;
        }

        public OpenAiResponseDTO.Builder choices(List<ChoiceDTO> choices) {
            this.choices = choices;
            return this;
        }

        public OpenAiResponseDTO.Builder serviceTier(String serviceTier) {
            this.serviceTier = serviceTier;
            return this;
        }

        public OpenAiResponseDTO.Builder fingerPrint(String fingerPrint) {
            this.fingerPrint = fingerPrint;
            return this;
        }

        public OpenAiResponseDTO build() {
            OpenAiResponseDTO openAiResponseDTO = new OpenAiResponseDTO();
            openAiResponseDTO.setId(id);
            openAiResponseDTO.setObject(object);
            openAiResponseDTO.setCreated(created);
            openAiResponseDTO.setModel(model);
            openAiResponseDTO.setChoices(choices);
            openAiResponseDTO.setServiceTier(serviceTier);
            openAiResponseDTO.setFingerPrint(fingerPrint);
            return openAiResponseDTO;
        }
    }
}
