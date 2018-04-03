package dal.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sun.media.jfxmedia.logging.Logger;
import works.maatwerk.generals.TileMapStage;
import works.maatwerk.generals.music.MusicManager;
import works.maatwerk.generals.utils.logger.*;

import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")
public class MapManager extends Stage {
    private final MusicManager musicManager;
    private TiledMap map;
    private final AssetManager assetManager;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private works.maatwerk.generals.models.Character characterSelected;
    @SuppressWarnings("CanBeFinal")
    private ArrayList<works.maatwerk.generals.models.Character> characterMap;
    private works.maatwerk.generals.models.Character[][] characterLayer;
    private static Vector2 mapDimensions;
    private final TileMapStage tileMapStage = new TileMapStage();
    private final InputMultiplexer multiplexer;
    private TextureRegion grid;


    /**
     * @param assetManager
     */
    public MapManager(AssetManager assetManager, InputMultiplexer inputMultiplexer, MusicManager musicManager) {
        this.assetManager = assetManager;
        this.multiplexer = inputMultiplexer;

        this.musicManager = musicManager;
        characterMap = new ArrayList<works.maatwerk.generals.models.Character>();
        this.grid = new TextureRegion();
    }


    /**
     * @param mapName
     */
    public void initializeMap(String mapName) {
        map = assetManager.get("speel_map2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        createMapActors(layer);
        characterLayer = new works.maatwerk.generals.models.Character[layer.getWidth()][layer.getHeight()];
        mapDimensions = new Vector2(layer.getWidth(), layer.getHeight());
        this.characterLayer = new works.maatwerk.generals.models.Character[layer.getWidth()][layer.getHeight()];
        this.initializeGrid();

        startMusic();

    }

    private void initializeGrid() {
        Gdx.app.debug("Grid", "Initializing Grid");
        Texture gridTexture = assetManager.get("GridGrayDotted.png");
        gridTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        MapProperties prop = map.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        grid = new TextureRegion(gridTexture, mapWidth * tilePixelWidth, mapHeight * tilePixelHeight);
    }


    private void createMapActors(TiledMapTileLayer layer) {

        multiplexer.addProcessor(tileMapStage);
        tileMapStage.createMapActors(layer, this);

    }

    /**
     *
     */
    private void startMusic() {
        Gdx.app.debug(Tag.MUSIC, "Starting Background Music");

        musicManager.stopAllMusic();
        if (map.getProperties().containsKey("BGM"))
            musicManager.playMusic(map.getProperties().get("BGM").toString());
        else
            musicManager.playRandomMusic();
    }

    /**
     * @param location
     */
    public void leftClickTile(Vector2 location) {


        works.maatwerk.generals.models.Character character = characterLayer[(int) location.x][(int) location.y];


        if (character != null && this.getCharacterSelected() == null) {
            this.setCharacterSelected(character);
            //TODO: UpdateUI
        } else {
            if (character == null && this.getCharacterSelected() !=null) {
                moveCharacter(this.getCharacterSelected(), location);
                this.setCharacterSelected(null);

            } else {
                if(this.getCharacterSelected() !=null){
                    this.getCharacterSelected().attack(character);
                    this.setCharacterSelected(null);
                }
            }
        }
    }


    /**
     * @param delta
     * @param camera
     * @param batch
     */
    public void render(final float delta, OrthographicCamera camera, final SpriteBatch batch) {
        tileMapStage.getViewport().setCamera(camera);
        renderer.setView(camera);
        renderer.render();
        tileMapStage.draw();
        batch.end();
        batch.begin();
        for (works.maatwerk.generals.models.Character c : this.characterMap) {
            c.draw(batch, delta);
        }
        batch.draw(grid, 0,0);
    }


    /**
     * @param character adds a character to the game
     */
    public void addCharacter(works.maatwerk.generals.models.Character character) {
        this.characterMap.add(character);
        this.characterLayer[(int) character.getLocation().x][(int) character.getLocation().y] = character;
    }

    /**
     * @param character removes character from the game
     */
    public void removeCharacter(works.maatwerk.generals.models.Character character) {
        if (character.getLocation() == null) {
            System.out.println("wtf");
        }
        this.characterLayer[(int) character.getLocation().x][(int) character.getLocation().y] = null;
        this.characterMap.remove(character);
    }

    /**
     * @param character
     * @param location
     */
    public void moveCharacter(works.maatwerk.generals.models.Character character, Vector2 location) {
        if (location.x > mapDimensions.x || location.y > mapDimensions.y || location.x < 0 || location.y < 0) {
            Logger.logMsg(1, "Out of boundaries");
            return;
        }
        removeCharacter(character);
        character.setLocation(location);
        addCharacter(character);
    }

    /**
     * @param id
     * @return
     */
    public works.maatwerk.generals.models.Character getCharacterById(int id) {
        return this.characterMap.get(id);
    }


    /**
     * @param location
     * @return cell from location
     */
    public TiledMapTileLayer.Cell getCell(Vector2 location) {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        @SuppressWarnings("UnnecessaryLocalVariable") TiledMapTileLayer.Cell cell = layer.getCell((int) location.x, (int) location.y);

        return cell;
    }

    /**
     * @return
     */
    public works.maatwerk.generals.models.Character getCharacterSelected() {
        return characterSelected;
    }

    /**
     * @param characterSelected
     */
    public void setCharacterSelected(works.maatwerk.generals.models.Character characterSelected) {
        this.characterSelected = characterSelected;
    }

    public void update(){
        ArrayList<works.maatwerk.generals.models.Character> remove = new ArrayList<works.maatwerk.generals.models.Character>();
        for (works.maatwerk.generals.models.Character c: characterMap){
            if(!c.isAlive()){
                characterLayer[(int)c.getLocation().x][(int)c.getLocation().y] = null;
                remove.add(c);
            }
        }
        for (works.maatwerk.generals.models.Character c:remove){
            characterMap.remove(c);

        }

    }
}
