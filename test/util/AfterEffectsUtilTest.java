/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import static avm.AVM.jsonStg;
import static avm.AVM.settings;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gab00
 */
public class AfterEffectsUtilTest {

    public AfterEffectsUtilTest() {
    }

    @Test
    public void testSomeMethod() {
        try {
            settings = ConfigUtil.getSettings("C://temp//settings.json");

            avm.AVM.settings.setFinalVideoConvertedPath(avm.AVM.settings.getFinalVideoPath().replace("finalVideo", "convertedFinalVideo"));
            String createTempBatEncodeFile = ScriptMaker.createTempBatEncodeFile(avm.AVM.settings.getFinalVideoPath(), avm.AVM.settings.getFinalVideoConvertedPath());
            HandbrakeUtil.convertFile(createTempBatEncodeFile);
        } catch (IOException ex) {
            Logger.getLogger(AfterEffectsUtilTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(AfterEffectsUtilTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
