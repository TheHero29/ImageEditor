import java.io.File; // bcoz we are reading input from a File
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;
import java.lang.*;
;
public class imageEditor2 {
    public static void printPixelValues(BufferedImage inputImage)
    {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++)
            {
                Color pixel = new Color(inputImage.getRGB(j, i));
                System.out.print("[" + pixel.getRed() + " " + pixel.getBlue() + " "+ pixel.getGreen() + "]");
            }
            System.out.println();
        }
    }

    public static BufferedImage convertToGrayScale(BufferedImage inputImage)
    {
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                outputImage.setRGB(j,i,inputImage.getRGB(j,i));
            }
        }
        return outputImage;
    }

   /*  public static void rotateImageLeft(BufferedImage inputImage)
    {
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        

    }
*/
    public static void reverseHorizontally(BufferedImage inputImage)
    {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height/2;y++)
            {
                int temp = inputImage.getRGB(x,y);
                inputImage.setRGB(x,y, inputImage.getRGB(x,(height-1)-y));
                inputImage.setRGB(x,(height-1)-y,temp);
            }
        }
    }
     
    public static void reverseVertically(BufferedImage inputImage)
    {
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width/2;j++)
            {
            int temp = inputImage.getRGB(j, i);
            inputImage.setRGB(j,i,inputImage.getRGB((width-1)-j,i));
            inputImage.setRGB((width-1)-j,i,temp);
            }
        }
    }

    //try to understand your own code bruh
    public static BufferedImage rotateImageRight(BufferedImage inputImage)
    {
        int height=inputImage.getHeight();
        int width=inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height,width,BufferedImage.TYPE_INT_RGB);
        for(int i=0;i<width;i++)
        {
            for(int j=0;j<height;j++)
            {
                outputImage.setRGB(j,i,inputImage.getRGB(i,j));
            }
        }
        reverseVertically(outputImage);
        return outputImage;
    }

    public static BufferedImage changeBrightness(BufferedImage inputImage,int change)
    {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                Color pixel = new Color(inputImage.getRGB(j,i));
                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();
                red = red + (red*change/100);
                green = green + (green*change/100);
                blue = blue + (blue*change/100);
                if(red>225)
                red=225;
                if(green>225)
                green=225;
                if(blue>225)
                blue=225;
                Color newpixel = new Color(red,blue,green);
                outputImage.setRGB(j,i,newpixel.getRGB());

            }
        }
        return outputImage;
    }

    public static void BlurImage(BufferedImage inputImage, int blurValue)
    {
        int a = blurValue;
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        for(int i=0;i<height/a;i++)
        {
            for(int j=0;j<width/a;j++)
            {
                int netRed=0,netGreen=0,netBlue=0;
                for(int i1=0;i1<a;i1++)
                {
                    for(int j1=0;j1<a;j1++)
                    {
                    Color pixel = new Color(inputImage.getRGB(a*j+j1,a*i+i1));
                    int red = pixel.getRed();
                    int green = pixel.getGreen();
                    int blue = pixel.getBlue();
                    netRed+=red;
                    netGreen+=green;
                    netBlue+=blue;
                    }
                }
                netRed=netRed/(a*a);
                netBlue=netBlue/(a*a);
                netGreen=netGreen/(a*a);
                Color newPixel = new Color(netRed,netGreen,netBlue);
                for(int i1=0;i1<a;i1++)
                {
                    for(int j1=0;j1<a;j1++)
                    {
                    inputImage.setRGB(a*j+j1,a*i+i1,newPixel.getRGB());
                    }
                }
            }
        }        
    }

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter The Pathname of the Image: ");
        String s = sc.nextLine();
        System.out.println("Select a operation to be performed:");
        System.out.println("Enter 1 for converting the image to grayscale");
        System.out.println("Enter 2 for rotating image clockwise by 90 degrees");
        System.out.println("Enter 3 for rotating image anti-clockwise by 90 degrees");
        System.out.println("Enter 4 for horizontally inverting the image");
        System.out.println("Enter 5 for vertically inverting the image");
        System.out.println("Enter 6 for changing brightness of the image");
        System.out.println("Enter 7 for blurring the image");
        System.out.println("Enter 8 to Exit");

        File inputFile = new File(s);
        try {
            BufferedImage inputImage = ImageIO.read(inputFile);
            int process=sc.nextInt();
            switch(process)
            {
                case 1:
                BufferedImage grayScale = convertToGrayScale(inputImage);
                File grayScaleImage = new File("grayScale-"+s);
                ImageIO.write(grayScale,"jpg",grayScaleImage);
                break;
                case 2:
                BufferedImage rotated = rotateImageRight(inputImage);
                File rotatedImage = new File("rotated-clockwise"+s);
                ImageIO.write(rotated,"jpg",rotatedImage);
                break;
                case 3:
                BufferedImage rotated3 = rotateImageRight(rotateImageRight(rotateImageRight(inputImage)));
                File rotated3Image = new File("rotated-anticlockwise"+s);
                ImageIO.write(rotated3,"jpg",rotated3Image);
                break;
                case 4:
                File HreversedImage = new File("horizontallyInversed"+s);
                reverseHorizontally(inputImage);
                ImageIO.write(inputImage,"jpg",HreversedImage);
                break;
                case 5:
                File VreversedImage = new File("verticallyInversed"+s);
                reverseVertically(inputImage);
                ImageIO.write(inputImage,"jpg",VreversedImage);
                break;
                case 6:
                System.out.println("Enter how much brighter the image should be: ");
                int change = sc.nextInt();
                BufferedImage brighter = changeBrightness(inputImage,change);
                File brighterImage = new File("brighter"+s);
                ImageIO.write(brighter,"jpg",brighterImage);
                break;
                case 7:
                System.out.println("Enter the level of blurness");
                int blurValue=sc.nextInt();
                BlurImage(inputImage,blurValue);
                File blurredImage = new File("blurred"+s);
                ImageIO.write(inputImage,"jpg",blurredImage);
                break;
                case 8:
                break;
                default:
                System.out.println("Enter a valid number");
            
            
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}
