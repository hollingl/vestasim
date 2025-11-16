package org.hollingdale.vestasim.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;

import org.hollingdale.vestasim.VestaCharacter;
import org.hollingdale.vestasim.VestasimConfiguration;

public class VestaBit extends JComponent
{
    private VestaCharacter target = VestaCharacter.BLANK;
    private VestaCharacter current = VestaCharacter.BLANK;

    public VestaBit()
    {
        setPreferredSize( new Dimension( 20, 40 ) );
    }

    public VestaBit setTarget( VestaCharacter target )
    {
        this.target = target;
        return this;
    }

    @Override
    protected void paintComponent( Graphics g )
    {
        super.paintComponent( g );

        g.setColor( Color.BLACK );
        g.fillRect( 0, 0, getWidth(), getHeight() );

        g.setColor( current.getColor() );

        var character = current.getCharacter();
        if ( character == ( char ) 0 )
        {
            // Draw block
        }
        else
        {
            // Draw character
            g.drawString( String.valueOf( character ), 5, 20 );
        }
    }

    public void transition()
    {
        new Thread( () -> {
            while ( current != target )
            {
                current = current.next();
                repaint();
                try
                {
                    Thread.sleep( VestasimConfiguration.FLIP_DELAY_MS );
                }
                catch ( InterruptedException ix )
                {
                    break;
                }
            }
        } ).start();
    }
}
