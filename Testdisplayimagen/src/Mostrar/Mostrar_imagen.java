package Mostrar;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;


public class Mostrar_imagen {
	
	static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	//Metodo1 que nos da error (de Mat a Buffered image
	public static BufferedImage passaraBufferedimage(Mat imagen)
	{
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if(imagen.channels()>1){ type = BufferedImage.TYPE_3BYTE_BGR;}
		
		int bufferSize = imagen.channels()*imagen.rows()*imagen.cols();
		byte[] b = new byte [bufferSize];
			
		imagen.get(0, 0, b);
		BufferedImage image = new BufferedImage(imagen.cols(), imagen.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;
			
	}
	
	//Metodo2 que nos permite pasar de Mat a Buffered image
	public static BufferedImage mat2Img(Mat input)
	{
		BufferedImage output;
		
		int cols = input.cols();
		int rows = input.rows();
		int channels = input.channels();
		
		byte[] data = new byte[cols*rows*channels];
		int type;
		input.get(0, 0,data);
		
		if(input.channels()== 1)
			type = BufferedImage.TYPE_BYTE_GRAY;
		else
			type = BufferedImage.TYPE_3BYTE_BGR;
		
		output = new BufferedImage(cols, rows, type);
		output.getRaster().setDataElements(0,0,cols,rows,data);
		return output;
	}
	
	
	//Metodo para pasar de Buffered image a Mat
	public static Mat img2Mat(BufferedImage in)
	{
		Mat out;
		byte[] data;
		int r,g,b;
		
		if(in.getType() == BufferedImage.TYPE_INT_RGB)
		{
			out = new Mat(240, 320, CvType.CV_8UC3);
			data = new byte[320*240*(int)out.elemSize()];
			int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
			for (int i = 0; i < dataBuff.length; i++) 
			{
				data[i*3] = (byte) ((dataBuff[i]>>16) & 0xFF);
				data[i*3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
				data[i*3 + 2] = (byte) ((dataBuff[i] >> 0) & 0xFF);
				
			}
		}
		else 
		{
			out = new Mat(240, 320, CvType.CV_8UC1);
			data = new byte[320*240*(int)out.elemSize()];
			int[] dataBuff = in.getRGB(0, 0, 320, 240, null, 0, 320);
			for(int i = 0; i < dataBuff.length; i++)
            {
              r = (byte) ((dataBuff[i] >> 16) & 0xFF);
              g = (byte) ((dataBuff[i] >> 8) & 0xFF);
              b = (byte) ((dataBuff[i] >> 0) & 0xFF);
              data[i] = (byte)((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
            }
		}
		out.put(0, 0, data);
		return out;
	}
	
	//With corrections
	public static Mat bufferdImg2Mat(BufferedImage in)
    {
          Mat out;
          byte[] data;
          int r, g, b;
          int height = in.getHeight();
          int width = in.getWidth();
          if(in.getType() == BufferedImage.TYPE_INT_RGB || in.getType() == BufferedImage.TYPE_INT_ARGB)
          {
              out = new Mat(height, width, CvType.CV_8UC3);
              data = new byte[height * width * (int)out.elemSize()];
              int[] dataBuff = in.getRGB(0, 0, width, height, null, 0, width);
              for(int i = 0; i < dataBuff.length; i++)
              {
                  data[i*3 + 2] = (byte) ((dataBuff[i] >> 16) & 0xFF);
                  data[i*3 + 1] = (byte) ((dataBuff[i] >> 8) & 0xFF);
                  data[i*3] = (byte) ((dataBuff[i] >> 0) & 0xFF);
              }
          }
          else
          {
              out = new Mat(height, width, CvType.CV_8UC1);
              data = new byte[height * width * (int)out.elemSize()];
              int[] dataBuff = in.getRGB(0, 0, width, height, null, 0, width);
              for(int i = 0; i < dataBuff.length; i++)
              {
                r = (byte) ((dataBuff[i] >> 16) & 0xFF);
                g = (byte) ((dataBuff[i] >> 8) & 0xFF);
                b = (byte) ((dataBuff[i] >> 0) & 0xFF);
                data[i] = (byte)((0.21 * r) + (0.71 * g) + (0.07 * b)); //luminosity
              }
           }
           out.put(0, 0, data);
           return out;
     }
	
	
	
	
	
	//Metodo que permite mostrar la imagen
	public static void displayImage(Image img)
	{
		ImageIcon icon = new ImageIcon(img);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(img.getWidth(null)+50, img.getHeight(null)+50);
		JLabel label = new JLabel();
		label.setIcon(icon);
		frame.add(label);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
