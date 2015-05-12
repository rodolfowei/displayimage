package Mostrar;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
//import com.atul.JavaOpenCV.Imshow;


public class MostratApp {
	
	

	public static void main(String[] args) {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	
		Mat imgd = new Mat(); 
		//Imshow im = new Imshow("Image");
		//im.Window.setResizable(true);
		
		
		imgd = Highgui.imread("C:/Users/CARLOS/Desktop/Proyecto lectura etiquetas/test_imagenes/amstel1.jpg", Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		
		BufferedImage imagen;
		
		imagen = Mostrar_imagen.mat2Img(imgd);
			
		System.out.println(imgd.rows());
		System.out.println(imgd.cols());
		
		System.out.println("test");
		
		Mostrar_imagen.displayImage(imagen);
		
		//im.showImage(imgd);
		

	}

}
