
package com.ceylinco.ceylincocustomerapp.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonPropertyOrder({
    "status",
    "amount",
    "remark",
    "brtel"
})
public class Data {

    @JsonProperty("status")
    private String status;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("remark")
    private Remark remark;
    @JsonProperty("brtel")
    private String brtel;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The amount
     */
    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The remark
     */
    @JsonProperty("remark")
    public Remark getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark
     *     The remark
     */
    @JsonProperty("remark")
    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    /**
     * 
     * @return
     *     The brtel
     */
    @JsonProperty("brtel")
    public String getBrtel() {
        return brtel;
    }

    /**
     * 
     * @param brtel
     *     The brtel
     */
    @JsonProperty("brtel")
    public void setBrtel(String brtel) {
        this.brtel = brtel;
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
