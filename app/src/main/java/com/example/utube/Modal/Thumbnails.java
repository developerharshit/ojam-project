
package com.example.utube.Modal;


public class Thumbnails {

    private Default _default;
    private Medium medium;
    private High high;
    private Standard standard;
    private Maxres maxres;

    public Default getDefault() {
        return _default;
    }

    public void setDefault(Default _default) {
        this._default = _default;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Maxres getMaxres() {
        return maxres;
    }

    public void setMaxres(Maxres maxres) {
        this.maxres = maxres;
    }

}
