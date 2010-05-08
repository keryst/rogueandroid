package com.stickycoding.Rokon.Backgrounds;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.stickycoding.Rokon.Background;
import com.stickycoding.Rokon.Debug;
import com.stickycoding.Rokon.Rokon;
import com.stickycoding.Rokon.Texture;
import com.stickycoding.Rokon.TextureAtlas;
import com.stickycoding.Rokon.TextureManager;
import com.stickycoding.Rokon.OpenGL.RokonRenderer;

/**
 * Tiled background generates the background from tilesets
 * Also can scroll the background according, redrawing tiles

 * @author Michael Lai
 */
public class ScrollingTiledBackground extends Background {

    private float _scrollX;
    private float _scrollY;

    private float _scaleX = 0.0f;
    private float _scaleY = 0.0f;
    private int _tileMap[][];  // Tile map that maps the textures
    private TextureAtlas _atlas;
    private int _xCoord = 0;
    private int _yCoord = 0;
    private int _xLength = 0;
    private int _yLength = 0;

    static private final boolean LOCAL_LOGGING = false;

    /**
     * Constructor with single texture map and tile Map
     * @param texture texture to tile
     * @param tileMap 2D integer array of tileMap
     * @param tileSizeX size of tile in X pixels
     * @param tileSizeY size of tile in Y pixels
     */
    public ScrollingTiledBackground(Texture texture, int[][] tileMap,
                                    float tileSizeX, float tileSizeY) {
        _tileMap = tileMap;
        _scrollX = 0;
        _scrollY = 0;

        _atlas = new TextureAtlas(512, 512);
        // only one texture to insert
        _atlas.insert(texture, 0, 0);
        texture.setTextureAtlas(_atlas);
        _scaleX = tileSizeX;
        _scaleY = tileSizeY;
        _xLength = (int) (Rokon.getRokon().getWidth() / _scaleY) + 2;
        _yLength = (int) (Rokon.getRokon().getHeight() / _scaleX) + 2;

        // Load image onto hardware
        TextureManager.load(_atlas);
    }

    /**
     * Constructor with textureArray texture[] and TileMap int[][]
     * @param textureArray array of textures to tile
     * @param tileMap 2D integer array of tileMap
     * @param tileSizeX size of tile in X pixels
     * @param tileSizeY size of tile in Y pixels
     */
    public ScrollingTiledBackground(Texture[] textureArray, int[][] tileMap,
                                    float tileSizeX, float tileSizeY) {
        _scrollX = 0;
        _scrollY = 0;
        _tileMap = tileMap;	
        // Just hard coding 1024 for now, probably need to calculate
        // the size of the atlas based upon X textures...
        _atlas = new TextureAtlas(512, 512);
        _scaleX = tileSizeX;
        _scaleY = tileSizeY;
        _xLength = (int) (Rokon.getRokon().getWidth() / _scaleY) + 2;
        _yLength = (int) (Rokon.getRokon().getHeight() / _scaleX) + 2;

        for ( int i = 0; i < textureArray.length; i++)
        {
            // insert the textures onto the hardware
            _atlas.insert(textureArray[i]);
            textureArray[i].setTextureAtlas(_atlas);
        }
        // Load image onto hardware
        TextureManager.load(_atlas);
    }

    /**
     * Constructor with textureMap Hashmap<Integer, Texture> and int[][] TileMap
     * @param textureMap Hashmap of textures
     * @param tileMap 2D integer array of tileMap
     * @param tileSizeX size of tile in X pixels
     * @param tileSizeY size of tile in Y pixels
     */	
    public ScrollingTiledBackground(HashMap<Integer, Texture> textureMap,
                                    int[][] tileMap, float tileSizeX,
                                    float tileSizeY) {
        _scrollX = 0;
        _scrollY = 0;
        _tileMap = tileMap;
        _atlas = new TextureAtlas(512, 512);
        _scaleX = tileSizeX;
        _scaleY = tileSizeY;
        Texture tempTexture;

        _xLength = (int) (Rokon.getRokon().getWidth() / _scaleY) + 2;
        _yLength = (int) (Rokon.getRokon().getHeight() / _scaleX) + 2;

        for (int i = 0; i < textureMap.size(); i++)
        {
            // insert the textures onto the hardware
            tempTexture = textureMap.get(i);
            _atlas.insert(tempTexture);
            tempTexture.setTextureAtlas(_atlas);
        }
        // Load image onto hardware
        TextureManager.load(_atlas);
    }

    /**
     * Default method for all background child classes
     * The setBackground method in Rokon calls this every X seconds
     * @param gl GL10
     */
    @Override
    public void drawFrame(GL10 gl) {

        int indexX = 0;
        int indexY = 0;
        int atlasIndex = 0;

        _atlas.select(gl);

        gl.glColor4f(1, 1, 1, 1);
        gl.glVertexPointer(2, GL11.GL_FLOAT, 0, RokonRenderer.vertexBuffer);
        gl.glLoadIdentity();

        // Looping to draw X by Y tile map
        for (_yCoord = 0; _yCoord < _yLength; _yCoord++)
        {
            for (_xCoord = 0; _xCoord < _xLength; _xCoord++)
            {
                if (LOCAL_LOGGING) {
                    Debug.print("Drawing tile at coords " +
                                                    _xCoord + ", " + _yCoord);
                }
                gl.glPushMatrix();
                indexX = roundScrollX(_xCoord);
                indexY = roundScrollY(_yCoord);
                atlasIndex = _tileMap[indexX][indexY];
                gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0,
                                                _atlas.
                                                textureAtIndex(atlasIndex)
                                                .getBuffer()
                                                .getBuffer());
                gl.glTranslatef(_xCoord * _scaleX +
                                                _scrollX % _scaleX,
                                                _yCoord * _scaleY
                                                + _scrollY % _scaleY,
                                                0.0f);
                gl.glScalef(_scaleX, _scaleY, 0.0f);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
                gl.glPopMatrix();
            }
        }
    }

    /**
     * Sets the scrolling offset of X and Y floats
     * @param x
     * @param y
     */
    public void setScroll(float x, float y) {
        if (LOCAL_LOGGING) {
            Debug.print("ScrollingTiledBackground.setScroll at x = " +
                                            ((Float)x).toString() +
                                            ", y = " + ((Float)y).toString());
        }
        _scrollX += x;
        _scrollY += y;
    }

    /**
     * Returns the scrolling X offset
     * @return
     */
    public float getScrollX() {
        return _scrollX;
    }

    /**
     * Returns the scrolling Y offset
     * @return
     */
    public float getScrollY() {
        return _scrollY;
    }
    
    /**
     * Contain the IndexX not to go out of bounds of tileMap array
     * @param xCoord
     * @return
     */
    private int roundScrollX(int xCoord) {
        int indexX = xCoord - (int) (_scrollX / _scaleX);
        
        if (indexX >= _tileMap.length)
        {
            return _tileMap.length - 1;
        }
        else if (indexX < 0)
        {
            return 0;
        }
        else
        {
            return indexX;
        }
    }

    /**
     * Contain the IndexY not to go out of bounds of tileMap array
     * @param yCoord
     * @return
     */
    private int roundScrollY(int yCoord) {
        int indexY = yCoord - (int) (_scrollY / _scaleY);
        
        if (indexY >= _tileMap[0].length)
        {
            return _tileMap[0].length - 1;
        }
        else if (indexY < 0)
        {
            return 0;
        }
        else
        {
            return indexY;
        }
    }
}