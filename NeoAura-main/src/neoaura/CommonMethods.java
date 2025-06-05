package neoaura;

import java.awt.*;

public class CommonMethods {
    public Dimension screensize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth = (int) (width * 0.7);
        int frameHeight = (int) (height * 0.7);
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension Fullscreensize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        return new Dimension(width,height);
    }
    public Dimension rightpanelsize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth = (int) (width * 0.25);
        int frameHeight = (int) (height * 0.7);
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension Fullrightpanelsize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth =(int)(width*0.2);
        return new Dimension(frameWidth,height);
    }
    public Dimension leftpanelsize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth = (int) (width * 0.45);
        int frameHeight = (int) (height * 0.7);
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension Fullleftpanelsize()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth =(int)(width*0.8);
        return new Dimension(frameWidth,height);
    }
    public Dimension halfcardpanel()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth =(int)(width*0.85);
        int frameHeight = (int) (height * 0.7);
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension fullcardpanel()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth =(int)(width*0.85);
        return new Dimension(frameWidth,height);
    }
    public Dimension halfbuttonpanel()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth =(int)(width*0.15);
        int frameHeight = (int) (height * 0.7);
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension fullbuttonpanel()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth =(int)(width*0.15);
        return new Dimension(frameWidth,height);
    }
    public Dimension halfchatpanel()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth =(int)(width*0.85);
        int frameHeight = (int) (height * 0.5);
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension fullchatpanel()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        int frameWidth =(int)(width*0.85);
        return new Dimension(frameWidth,height);
    }
    public Dimension halfbottompanel()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int frameWidth =(int)(width*0.6);
        int frameHeight = 40;
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension fullbottompanel()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int frameWidth =(int)(width*0.8);
        int frameHeight = 40;
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension textfield()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int frameWidth =(int)(width*0.6);
        int frameHeight = 40;
        return new Dimension(frameWidth,frameHeight);
    }
    public Dimension fulltextfield()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int frameWidth =(int)(width*0.8);
        int frameHeight = 40;
        return new Dimension(frameWidth,frameHeight);
    }
}
