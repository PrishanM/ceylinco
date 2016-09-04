
package com.ceylinco.ceylincocustomerapp.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nameofinsured",
    "period",
    "status",
    "branch",
    "brtel",
    "rendate",
    "renprem",
    "rensum",
    "intvres"
})
public class PolicyData {

    @JsonProperty("nameofinsured")
    private String nameofinsured;
    @JsonProperty("period")
    private String period;
    @JsonProperty("status")
    private String status;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("brtel")
    private String brtel;
    @JsonProperty("rendate")
    private String rendate;
    @JsonProperty("renprem")
    private String renprem;
    @JsonProperty("rensum")
    private String rensum;
    @JsonProperty("intvres")
    private String intvres;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The nameofinsured
     */
    @JsonProperty("nameofinsured")
    public String getNameofinsured() {
        return nameofinsured;
    }

    /**
     * 
     * @param nameofinsured
     *     The nameofinsured
     */
    @JsonProperty("nameofinsured")
    public void setNameofinsured(String nameofinsured) {
        this.nameofinsured = nameofinsured;
    }

    /**
     * 
     * @return
     *     The period
     */
    @JsonProperty("period")
    public String getPeriod() {
        return period;
    }

    /**
     * 
     * @param period
     *     The period
     */
    @JsonProperty("period")
    public void setPeriod(String period) {
        this.period = period;
    }

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
     *     The branch
     */
    @JsonProperty("branch")
    public String getBranch() {
        return branch;
    }

    /**
     * 
     * @param branch
     *     The branch
     */
    @JsonProperty("branch")
    public void setBranch(String branch) {
        this.branch = branch;
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

    /**
     * 
     * @return
     *     The rendate
     */
    @JsonProperty("rendate")
    public String getRendate() {
        return rendate;
    }

    /**
     * 
     * @param rendate
     *     The rendate
     */
    @JsonProperty("rendate")
    public void setRendate(String rendate) {
        this.rendate = rendate;
    }

    /**
     * 
     * @return
     *     The renprem
     */
    @JsonProperty("renprem")
    public String getRenprem() {
        return renprem;
    }

    /**
     * 
     * @param renprem
     *     The renprem
     */
    @JsonProperty("renprem")
    public void setRenprem(String renprem) {
        this.renprem = renprem;
    }

    /**
     * 
     * @return
     *     The rensum
     */
    @JsonProperty("rensum")
    public String getRensum() {
        return rensum;
    }

    /**
     * 
     * @param rensum
     *     The rensum
     */
    @JsonProperty("rensum")
    public void setRensum(String rensum) {
        this.rensum = rensum;
    }

    /**
     * 
     * @return
     *     The intvres
     */
    @JsonProperty("intvres")
    public String getIntvres() {
        return intvres;
    }

    /**
     * 
     * @param intvres
     *     The intvres
     */
    @JsonProperty("intvres")
    public void setIntvres(String intvres) {
        this.intvres = intvres;
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
