package sw.world.blocks.force;

import arc.graphics.g2d.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import sw.util.*;
import sw.world.interfaces.*;
import sw.world.meta.*;
import sw.world.modules.*;

import static sw.world.modules.ForceModule.*;

public class ForceNode extends Block {
	public ForceConfig forceConfig = new ForceConfig();

	public ForceNode(String name) {
		super(name);
		solid = destructible = true;
		sync = update = true;
		configurable = true;
	}

	@Override public void drawOverlay(float x, float y, int rotation) {
		Drawf.dashCircle(x, y, forceConfig.range, Pal.accent);
	}

	@Override
	public void setStats() {
		super.setStats();
		forceConfig.addStats(stats);
	}

	public class ForceNodeBuild extends Building implements HasForce {
		ForceModule force = new ForceModule();

		public ForceModule force() {
			return force;
		}
		public ForceConfig forceConfig() {return forceConfig;}

		@Override
		public void draw() {
			super.draw();
			drawBelt(this);
		}
		@Override
		public void drawConfigure() {
			drawOverlay(x, y, 0);
			SWDraw.square(Pal.accent, x, y, block.size * 6f, 0f);
			if (getLink() != null) SWDraw.square(Pal.place, getLink().x, getLink().y, getLink().block.size * 6f, 0f);
			Draw.reset();
		}

		@Override
		public void onProximityAdded() {
			super.onProximityAdded();
			force.graph.floodFill(this).each(b -> graph().add(b));
		}
		@Override
		public void onProximityRemoved() {
			super.onProximityRemoved();
			force().graph.removeBuild(this);
		}

		@Override
		public boolean onConfigureBuildTapped(Building other) {
			if (other instanceof HasForce next && tile.dst(other) < forceConfig().range) {
				if ((other == this || getLink() == other) && getLink() != null) {
					graph().removeBuild(other);
					graph().remove(other);
					force().links.remove(new Link(this, getLink()));
					((HasForce) getLink()).force().links.remove(new Link(getLink(), this));
					force().link = -1;
					return false;
				}

				if (next.force().link == pos()) {
					next.force().links.remove(new Link(other, this));
					force().links.remove(new Link(this, other));
					next.force().link = -1;
					graph().removeBuild(other);
					graph().remove(other);
				}

				force().link = other.pos();
				force().links.addUnique(new Link(this, other));
				next.force().links.addUnique(new Link(other, this));
				graph().addGraph(((HasForce) getLink()).graph());
				graph().rotation = 0;
				return false;
			}
			return true;
		}

		@Override
		public void read(Reads read, byte revision) {
			super.read(read, revision);
			force.read(read);
			graph().floodFill(this).each(b -> graph().add(b));
		}
		@Override
		public void write(Writes write) {
			super.write(write);
			force.write(write);
		}
	}
}
