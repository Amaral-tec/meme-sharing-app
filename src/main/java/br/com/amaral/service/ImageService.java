package br.com.amaral.service;

import org.springframework.stereotype.Service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import br.com.amaral.model.Meme;

@Service
public class ImageService {

	public Meme saveMemeImages(Meme meme) throws IOException {

		for (int x = 0; x < meme.getImages().size(); x++) {
			meme.getImages().get(x).setMeme(meme);

			String base64Image = "";

			if (meme.getImages().get(x).getOriginalImage().contains("data:image")) {
				base64Image = meme.getImages().get(x).getOriginalImage().split(",")[1];
			} else {
				base64Image = meme.getImages().get(x).getOriginalImage();
			}

			byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

			if (bufferedImage != null) {

				int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
				int width = Integer.parseInt("800");
				int height = Integer.parseInt("600");

				BufferedImage resizedImage = new BufferedImage(width, height, type);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(bufferedImage, 0, 0, width, height, null);
				g.dispose();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(resizedImage, "png", baos);

				meme.getImages().get(x).setThumbnailImage(base64Image);

				bufferedImage.flush();
				resizedImage.flush();
				baos.flush();
				baos.close();
			}
		}
		return meme;
	}
	
}
