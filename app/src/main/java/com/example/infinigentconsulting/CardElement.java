package com.example.infinigentconsulting;

public class CardElement {
    private String _component_name;
    private int _component_img;

    public CardElement() {
    }

    public CardElement(String _component_name, int _component_img) {
        this._component_name = _component_name;
        this._component_img = _component_img;
    }

    public String get_component_name() {
        return _component_name;
    }

    public void set_component_name(String _component_name) {
        this._component_name = _component_name;
    }


    public int get_component_img() {
        return _component_img;
    }

    public void set_component_img(int _component_img) {
        this._component_img = _component_img;
    }
}
