package com.example.app_desktop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MyMouseListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent evt) {
        System.out.println("bazinga");
        try {
            File audioFile = new File("sounds/doFlauta.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());

        }
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        System.out.println("bazinga");
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        System.out.println("bazinga");

    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        System.out.println("bazinga");

    }

    @Override
    public void mouseExited(MouseEvent evt) {
        System.out.println("bazinga");

    }
}