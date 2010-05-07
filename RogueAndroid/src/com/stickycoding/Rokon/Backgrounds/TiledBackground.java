package com.stickycoding.Rokon.Backgrounds;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.OpenGL.RokonRenderer;

/**
 * Tiled background generates the background from tilesets
 *
 * @author Michael Lai
 */
public class TiledBackground extends Background {

    private TextureAtlas _atlas;
    private int _xCoord = 0;
    private int _yCoord = 0;
    private float _scaleX = 0.0f;
    private float _scaleY = 0.0f;	
    private int _xLength = 0;
    private int _yLength = 0;

    private int _tileMap[][];  // Tile map that maps the textures	

    /**
     * Constructor for an array of textures
     * @param textureArray Array of textures to use
     * @param tileMap 2D Integer array that maps which tiles to which location
     * @param tileSizeX tilesize along X
     * @param tileSizeY tilesize along Y
     */
    public TiledBackground(Texture[] textureArray, int[][] tileMap,
                                    int tileSizeX, int tileSizeY) {

        _tileMap = tileMap;
        // Just hard coding 512 for now, probably need to calculate
        // the size of the atlas based upon X textures...
        _atlas = new TextureAtlas(512, 512);
        _scaleX = tileSizeX;
        _scaleY = tileSizeY;
        _xLength = (int) (Rokon.getRokon().getWidth() / _scaleY);
        _yLength = (int) (Rokon.getRokon().getHeight() / _scaleX);

        for ( int i = 0; i < textureArray.length; i++)
        {
            // insert the textures onto the hardware
            _atlas.insert(textureArray[i]);
        }

        // Load images onto hardware
        TextureManager.load(_atlas);
    }

    /**
     * Constructor for a textureMap texture
     * @param textureMap Texture which contains multiple tiles
     * @param tileMap 2D Integer array that maps which tiles to which location
     * @param tileSizeX tilesize along X
     * @param tileSizeY tilesize along Y
     */
    public TiledBackground(Texture textureMap, int [][] tileMap, int tileSizeX,
                                    int tileSizeY) {

        _tileMap = tileMap;
        // Just hard coding 512 for now, probably need to calculate
        // the size of the atlas based upon texturesMap size
        _atlas = new TextureAtlas(512, 512);
        _atlas.insert(textureMap, 0, 0);
        _scaleX = tileSizeX;
        _scaleY = tileSizeY;
        _xLength = (int) (Rokon.getRokon().getWidth() / _scaleY);
        _yLength = (int) (Rokon.getRokon().getHeight() / _scaleX);

        // Load image onto hardware
        TextureManager.load(_atlas);
    }

    /**
     * Default method for all background child classes
     * The setBackground method in Rokon calls this every X seconds
     */
    @Override
    public void drawFrame(GL10 gl) {

        _atlas.select(gl);	

        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glVertexPointer(2, GL11.GL_FLOAT, 0, RokonRenderer.vertexBuffer);
        gl.glLoadIdentity();
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // Looping to draw X by Y tile map
        for (_yCoord = 0; _yCoord < _yLength; _yCoord++)
        {
            for (_xCoord = 0; _xCoord < _xLength; _xCoord++)
            {	
                gl.glPushMatrix();
                gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, _atlas.textureAtIndex(_tileMap[_xCoord][_yCoord]).getBuffer().getBuffer());
                // http://iphonedevelopment.blogspot.com/2009/05/opengl-es-from-ground-up-part-6_25.html
                gl.glTranslatef(_xCoord*_scaleX, _yCoord*_scaleY, 0.0f);
                gl.glScalef(_scaleX, _scaleY, 0);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
                gl.glPopMatrix();
            }
        }
    }
}