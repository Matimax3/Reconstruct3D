
package reconstruct3d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class TextureGen extends JComponent{
    
    private float texture_width = 100;
    private float texture_height = 100;
    BufferedImage offImg;
    Color[] colorPalette;
    
    public BufferedImage horizontalflip(BufferedImage img) {  
        int w = img.getWidth();  
        int h = img.getHeight();  
        BufferedImage dimg = new BufferedImage(w, h, img.getType());  
        Graphics2D g = dimg.createGraphics();  
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);  
        g.dispose();  
        return dimg;  
    }
    
    Graphics2D createCanvas(){
        Graphics2D g2 = null;
        
        //offImg = (BufferedImage)createImage((int)texture_width, (int)texture_height);
        offImg = new BufferedImage((int)texture_width, (int)texture_height, BufferedImage.TYPE_INT_ARGB);
        
        g2 = offImg.createGraphics();
        g2.setBackground(Color.white);

        // .. set attributes ..
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
        // .. clear canvas ..
        g2.clearRect(0, 0, getSize().width, getSize().height);

        return g2;
    }
    
    void initColorPalette(){
        int col_num = 1000;
        colorPalette = new Color[col_num];
        Random rand = new Random();
        
        for(int i = 0; i < col_num; i++)
            colorPalette[i] = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 255);
        
    }
    
    Color genColor(int id){
        Color gen;
        Random rand = new Random();
        
        int mn = rand.nextInt(5);
        gen = colorPalette[id];
        
        return gen;
    }
    
    void generate(Slice[] slices, int count){
        Graphics2D g2 = (Graphics2D)createCanvas();
        initColorPalette();
        
        for(int i = 0; i < count; i++){
            for(int j = 0; j < slices[i].sliceCells.size(); j++){
                if(slices[i].sliceCells.get(j).id == 0)
                    g2.setColor(Color.white);
                if(slices[i].sliceCells.get(j).id == 1)
                    g2.setColor(Color.black);
                else
                    g2.setColor(genColor(slices[i].sliceCells.get(j).id));
//                if(slices[i].sliceCells.get(j).id == 2)
//                    g2.setColor(Color.red);
//                if(slices[i].sliceCells.get(j).id == 3)
//                    g2.setColor(Color.green);
//                if(slices[i].sliceCells.get(j).id == 4)
//                    g2.setColor(Color.yellow);
//                if(slices[i].sliceCells.get(j).id == 5)
//                    g2.setColor(Color.pink);
                
                int x = slices[i].sliceCells.get(j).x;
                int y = slices[i].sliceCells.get(j).y;
                
                g2.fillRect(x, y, 1, 1);
            }
            
            File output = new File("Regen/VolumeSplices/"+""+i+".png");
            try {
                ImageIO.write(offImg, "png", output);
            } catch (IOException ex) {
                Logger.getLogger(TextureGen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    
    }
    
}