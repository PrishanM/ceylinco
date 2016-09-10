
package com.ceylinco.ceylincocustomerapp.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "status",
    "reference",
    "error",
        "policy",
        "claim",
        "data"
})
public class Results {

    @JsonProperty("status")
    private String status;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("error")
    private Error error;
    @JsonProperty("policy")
    private List<Policy> policy = new ArrayList<Policy>();
    @JsonProperty("claim")
    private List<Claim> claim = new ArrayList<Claim>();
    @JsonProperty("data")
    private Data data;
    @JsonProperty("type")
    private List<PerilType> type = new ArrayList<PerilType>();
    @JsonIgnore
    private final Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The reference
     */
    @JsonProperty("reference")
    public String getReference() {
        return reference;
    }

    /**
     * 
     * @param reference
     *     The reference
     */
    @JsonProperty("reference")
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * 
     * @return
     *     The error
     */
    @JsonProperty("error")
    public Error getError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    @JsonProperty("error")
    public void setError(Error error) {
        this.error = error;
    }

    /**
     *
     * @return
     * The policy
     */
    @JsonProperty("policy")
    public List<Policy> getPolicy() {
        return policy;
    }

    /**
     *
     * @param policy
     * The policy
     */
    @JsonProperty("policy")
    public void setPolicy(List<Policy> policy) {
        this.policy = policy;
    }

    /**
     *
     * @return
     *     The claim
     */
    @JsonProperty("claim")
    public List<Claim> getClaim() {
        return claim;
    }

    /**
     *
     * @param claim
     *     The claim
     */
    @JsonProperty("claim")
    public void setClaim(List<Claim> claim) {
        this.claim = claim;
    }

    /**
     *
     * @return
     *     The data
     */
    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     *     The data
     */
    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
    }

    /**
     *
     * @return
     *     The type
     */
    @JsonProperty("type")
    public List<PerilType> getType() {
        return type;
    }

    /**
     *
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(List<PerilType> type) {
        this.type = type;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
