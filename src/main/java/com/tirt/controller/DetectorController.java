package com.tirt.controller;

import javafx.fxml.FXML;
import com.tirt.model.DetectorModel;

/**
 * Created by Kuba on 26.03.2016.
 */
public class DetectorController {

    private DetectorModel detectorModel;

    public DetectorController(DetectorModel detectorModel){
        this.detectorModel = detectorModel;
    }

    @FXML
    private void onOpen(){
        System.out.println("onOpen");
    }

    @FXML
    private void onClose(){
        System.out.println("onClose");
    }
}
