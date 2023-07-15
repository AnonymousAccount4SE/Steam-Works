package sw.world.graph;

import arc.util.*;
import sw.entities.comp.*;

public abstract class Graph {
	public final @Nullable GraphUpdater entity;

	public Graph() {
		entity = new GraphUpdater();
		entity.graph = this;
	}

	public void addGraph() {
		entity.add();
	}
	public void removeGraph() {
		entity.remove();
	}

	public abstract void update();
}
