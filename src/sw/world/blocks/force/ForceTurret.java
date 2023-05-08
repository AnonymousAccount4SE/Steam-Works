package sw.world.blocks.force;

import arc.struct.*;
import arc.util.io.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import sw.world.graph.*;
import sw.world.interfaces.*;
import sw.world.meta.*;
import sw.world.modules.*;

public class ForceTurret extends Turret {
	public ForceConfig forceConfig = new ForceConfig();
	public BulletType shootType;

	public ForceTurret(String name) {
		super(name);
	}

	@Override public void drawOverlay(float x, float y, int rotation) {
		Drawf.dashCircle(x, y, forceConfig.range, Pal.accent);
	}

	@Override
	public void setStats() {
		super.setStats();
		forceConfig.addStats(stats);
		stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(this, shootType)));
	}

	public class ForceTurretBuild extends TurretBuild implements HasForce {
		ForceModule force = new ForceModule();

		public ForceModule force() {
			return force;
		}
		public ForceConfig forceConfig() {return forceConfig;}

		@Override
		public void updateTile() {
			unit.ammo(speed() * unit.type().ammoCapacity);
			super.updateTile();
		}

		@Override public BulletType peekAmmo() {
			return shootType;
		}
		@Override public BulletType useAmmo() {
			return shootType;
		}
		@Override public boolean hasAmmo() {
			return true;
		}

		@Override
		public void onProximityAdded() {
			super.onProximityAdded();
			force.graph.floodFill(this).each(b -> graph().add(b));
		}
		@Override
		public void onProximityRemoved() {
			super.onProximityRemoved();
			force().links.each(Link::removeS);
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
