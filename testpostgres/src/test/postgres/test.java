package test.postgres;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;


import org.apache.commons.io.IOUtils;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;



public class test {
public static void main(String args[]) throws SQLException
{
	try
	{
	DatabaseConnection	db=new DatabaseConnection();
BufferedImage image = null;

Connection con= db.getConnection();
con.setAutoCommit(false);
LargeObjectManager lobj = ((org.postgresql.PGConnection)con).getLargeObjectAPI();
PreparedStatement ps = con.prepareStatement("SELECT image FROM test WHERE id = ?");
ps.setInt(1,1);
System.out.println("1>>>>");
ResultSet rs = ps.executeQuery();

 
System.out.println("2");
if (rs != null) {
	while(rs.next()) {
		
		int oid = rs.getInt(1);
		System.out.println("--->"+oid);
		
	     LargeObject obj = lobj.open(oid, LargeObjectManager.READ);
   
	   
	      byte buf[] = new byte[obj.size()];
	      obj.read(buf, 0, obj.size());
	     
	      
	      InputStream in = new ByteArrayInputStream(buf);
	      BufferedImage bufferedImage = ImageIO.read(in);
	    
	      ImageIO.write(bufferedImage, "jpg", new File("super152.jpg"));
	      
	    
	  }
	     }
	}
	catch(Exception e){
		e.printStackTrace();
	}
	}
	} 
   

