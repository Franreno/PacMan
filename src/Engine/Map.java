package Engine;

 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.IndexOutOfBoundsException;

/**
 *
 * @author franreno
 */
public class Map {
    private int mapHeight;
    private int mapWidth;
    private int[][] mapData;
    
    public Map(String levelName) {
        JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader("levels.json")) {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            
            
            this.mapHeight = (int) (long) obj.get(levelName + "_Height");
            this.mapWidth = (int) (long) obj.get(levelName + "_Width");
            mapData = new int[this.mapHeight][this.mapWidth];
            
            JSONArray data = (JSONArray) obj.get(levelName + "_Data");
            
            for(int i=0; i<this.mapHeight; i++) {
                JSONArray rowData = (JSONArray) data.get(i);
                for(int j=0; j<this.mapWidth; j++) {
                    mapData[i][j] = (int) (long) rowData.get(j);
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
    }
    
    public int getHeight() {
        return this.mapHeight;
    }
    
    public int getWidth() {
        return this.mapWidth;
    }
    
    public int[][] getData() {
        return this.mapData;
    }
    
    public void setValueAtMap(int value, int x, int y) {
        // Verificar se a posicao eh valida
        if( x > this.mapWidth || x < 0) 
            throw new IndexOutOfBoundsException();
        if( y > this.mapHeight || x < 0)
            throw new IndexOutOfBoundsException();
        if( this.mapData[x][y] == 6 || this.mapData[x][y] == 4)
            throw new IndexOutOfBoundsException();
        
        this.mapData[x][y] = value;
        
    }
    
    private void printTargetMap(int value) {
        switch(value) {
            //Empty pixel
            case 0 -> 
                System.out.print("  ");
            //Food pixel
            case 1 -> 
                System.out.print("· ");
            //Poweup pixel
            case 2 -> 
                System.out.print("@ ");
            //Fruit pixel
            case 3 -> 
                System.out.print("F ");
            //Wall pixel
            case 4 -> 
                System.out.print("■ ");
            //Ghost zone pixels
            case 5 -> System.out.print("  ");
            //Void pixels
            case 6 -> System.out.print("  ");
            //PacMan
            case 10 -> System.out.print("® ");
            
        }
    }
    
    public void printMap() {
        for(int i=0; i<mapHeight; i++) {
            for(int j=0; j<mapWidth; j++){
                printTargetMap(mapData[i][j]);
            }
            System.out.print("\n");
        }
    }
    
   public int countAmountOfLegalPlaces() {
       int count = 0;
       for(int i=0; i<mapHeight; i++) {
           for(int j=0; j<mapWidth; j++) {
               if(mapData[i][j] != 4 && mapData[i][j] != 6) {
                   count++;
               }
           }
       }
       
       return count;
   }
    
}
