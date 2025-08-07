package hust.ccs;

import com.jme3.scene.Node;

/**
 * Interface to the scene-graph representation of a game object.
 *
 * */
public interface HasNode {
    /**
     * Access the scene-graph subtree that represents this game object.
     *
     * @return the pre-existing instance (not null)
     */
    Node getNode();
}
