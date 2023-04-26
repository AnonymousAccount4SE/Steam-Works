package sw.world.blocks.heat;

import arc.Core;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.StatUnit;
import sw.SWVars;
import sw.world.interfaces.HasHeat;
import sw.world.interfaces.HeatBlockI;
import sw.world.meta.HeatConfig;
import sw.world.meta.SWStat;
import sw.world.modules.HeatModule;

public class HeatGenericCrafter extends GenericCrafter implements HeatBlockI {
  HeatConfig heatConfig = SWVars.baseConfig.copy();
  public float outputHeat = -1f;

  public HeatGenericCrafter(String name) {
    super(name);
  }

  @Override public HeatConfig heatConfig() {return heatConfig;}

  @Override
  public void setStats() {
    super.setStats();
    heatStats(stats);
    if (outputHeat >= 0) stats.add(SWStat.outputHeat, outputHeat, StatUnit.degrees);
  }
  @Override
  public void setBars() {
    super.setBars();
    addBar("heat", (HeatGenericCrafterBuild entity) -> new Bar(Core.bundle.get("bar.heat"), Pal.accent, entity::fraction));
  }

  public class HeatGenericCrafterBuild extends GenericCrafterBuild implements HasHeat {
    HeatModule module = new HeatModule();

    @Override public HeatModule module() {
      return module;
    }
    @Override public HeatBlockI type() {
      return (HeatBlockI) block;
    }

    @Override
    public void updateTile() {
      super.updateTile();
      if (efficiency > 0 && outputHeat >= 0) module().addHeat(outputHeat * efficiencyScale() * Time.delta);
      updateHeat(this);
    }

    @Override
    public void write(Writes write) {
      super.write(write);
      module().write(write);
    }
    @Override
    public void read(Reads read, byte revision) {
      super.read(read, revision);
      module().read(read);
    }
  }
}
