/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.academico.garagem.model.service;

import com.academico.garagem.model.base.BaseService;
import com.academico.garagem.model.base.service.BaseImageService;
import com.academico.garagem.model.dao.ImageDAO;
import com.academico.garagem.model.entity.Image;
import com.academico.garagem.model.service.exceptions.NonexistentEntityException;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class ImageService extends BaseService implements Serializable, BaseImageService {

    private static final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
    private static final int[] RGBA_MASKS = {0xFF0000, 0xFF00, 0xFF, 0xFF000000};
    private static final ColorModel OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
    private static final ColorModel TRANSLUCENT = new DirectColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), 32, RGBA_MASKS[0], RGBA_MASKS[1], RGBA_MASKS[2], RGBA_MASKS[3], true, DataBuffer.TYPE_INT);

    public ImageService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Image image) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImageDAO dao = new ImageDAO();
            dao.create(em, image);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Image> findAll() throws Exception {
        return findEntities(true, -1, -1);
    }

    @Override
    public List<Image> findEntities(int maxResults, int firstResult) throws Exception {
        return findEntities(false, maxResults, firstResult);
    }

    private List<Image> findEntities(boolean all, int maxResults, int firstResult) throws Exception {
        EntityManager em = getEntityManager();
        try {
            ImageDAO dao = new ImageDAO();
            return dao.findEntities(em, all, maxResults, firstResult);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Image> findByCriteria(Map<Integer, Object> criteria, int limit, int offset) throws Exception {
        EntityManager em = getEntityManager();
        try {
            ImageDAO dao = new ImageDAO();
            return dao.findByCriteria(em, criteria, limit, offset);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Image findEntity(String id) throws Exception {
        EntityManager em = getEntityManager();
        try {
            ImageDAO dao = new ImageDAO();
            return dao.findEntity(em, id);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public int getEntityCount() throws Exception {
        EntityManager em = getEntityManager();
        try {
            ImageDAO dao = new ImageDAO();
            return dao.getEntityCount(em);
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void edit(Image image) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImageDAO dao = new ImageDAO();
            dao.edit(em, image);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = image.getSrc();
                if (findEntity(id) == null) {
                    throw new NonexistentEntityException("The image with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(String id) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImageDAO dao = new ImageDAO();
            dao.destroy(em, id);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction() != null) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public Map<String, String> validate(Map<String, Object> fields) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Salva uma foto
     *
     * @param path caminho do arquivo de imagem
     * @throws Exception
     */
    @Override
    public void savePhoto(byte[] bytes, String path) throws Exception {
        File f = new File(path);
        java.awt.Image img = Toolkit.getDefaultToolkit().createImage(bytes);

        PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, true);
        pg.grabPixels();
        int width = pg.getWidth(), height = pg.getHeight();

        DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
        BufferedImage bi;
        if (FilenameUtils.getExtension(f.getName()).equals("png")) {
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGBA_MASKS, null);
            bi = new BufferedImage(ColorModel.getRGBdefault(), raster, false, null);
        } else {
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            bi = new BufferedImage(OPAQUE, raster, false, null);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(bi, FilenameUtils.getExtension(f.getName()), baos);
        bytes = baos.toByteArray();

        // Salva dados no arquivo (no caso uma imagem)
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
        stream.write(bytes);
        stream.close();
    }

    /**
     * retorna arquivo com a foto
     *
     * @param path caminho do arquivo de imagem
     * @return File representando a foto do usuario, null caso o usuario nao
     * possua foto
     * @throws Exception
     */
    @Override
    public File getPhoto(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            file = null;
        }
        return file;
    }

    /**
     * Apaga a foto
     *
     * @param path caminho do arquivo de imagem
     * @throws Exception
     */
    @Override
    public void deletePhoto(String path) throws Exception {
        File file = getPhoto(path);
        if (file != null && file.exists()) {
            file.delete();
        }
    }

}
