package com.umi.saveLoad;

import UIFrame.ItemImage;
import com.umi.entity.Bullet;
import com.umi.entity.Creature;
import com.umi.frame.Thing;
import com.umi.frame.World;

import java.io.*;

public class SaveAndLoad {
    private World world;
    private String savePath;
    private String prefix = "Server/src/main/resources/";

    public SaveAndLoad(World world) {
        this(world, "save/game.save");
    }

    public SaveAndLoad(World world, String savePath) {
        this.world = world;
        this.savePath = savePath;
    }

    public void save() {
        try (FileOutputStream fos = new FileOutputStream(savePath)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Data data = new Data(world);
            oos.writeObject(data);
        } catch (Exception e) {
            try (FileOutputStream fos = new FileOutputStream(prefix + savePath)) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                Data data = new Data(world);
                oos.writeObject(data);
            } catch (Exception ee) {
                ee.printStackTrace();
                e.printStackTrace();
            }
        }
    }

    public void load() throws Exception {
        Data data;
        try (FileInputStream fis = new FileInputStream(savePath)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (Data) ois.readObject();
        } catch (Exception e) {
            try (FileInputStream fis = new FileInputStream((prefix + savePath))) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                data = (Data) ois.readObject();
            } catch (Exception ee) {
                e.printStackTrace();
                throw ee;
            }
        }

        for(int i = 0;i < World.WIDTH;i++) {
            for(int j = 0;j < World.HEIGHT;j++) {
                Thing groundThing = data.groundThings[i][j];
                Thing midThing = data.midThings[i][j];
                Thing skyThing = data.skyThings[i][j];
                if(groundThing != null) {
                    groundThing.setItemImage(new ItemImage(groundThing.getImageFilename()));
                    groundThing.setWorld(world);
                    world.putInGround(groundThing, i, j);
                }
                if(midThing != null) {
                    midThing.setItemImage(new ItemImage(midThing.getImageFilename()));
                    midThing.setWorld(world);
                    world.putInMid(midThing, i, j);
                }
                if(skyThing != null) {
                    skyThing.setItemImage(new ItemImage(skyThing.getImageFilename()));
                    skyThing.setWorld(world);
                    world.putInSky(skyThing, i, j);
                }
            }
        }
        world.setSecondGourd(data.theSecondGourd);
        world.setFirstGourd(data.theFirstGourd);

        for(Creature creature : data.creatures) {
            new Thread(creature).start();
            world.addCreature(creature);
            if(creature.getBullets().size() != 0) {
                for(Bullet bullet : creature.getBullets()) {
                    new Thread(bullet).start();
                }
            }
        }
    }
}
