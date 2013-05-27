/*
 * Copyright (c) 2013 Aritzh (Aritz Lopez)
 *
 * This game is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This game is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this
 * game. If not, see http://www.gnu.org/licenses/.
 */

package aritzh.waywia.universe;

import aritzh.waywia.bds.BDSCompound;
import aritzh.waywia.bds.BDSString;
import org.newdawn.slick.Graphics;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class Universe {

	public static FileFilter onlyFolders = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};

	private File root;

	private Set<World> worlds = new HashSet<>();
	private World currentWorld;

	private String name;
	private BDSCompound customData;

	public Universe(BDSCompound compound, File root) {
		if (!root.mkdir()) throw new RuntimeException("Couldn't make root folder for universe " + this);
		this.root = root;
		this.parseUniverse(compound);
		this.worlds.addAll(World.getWorldList(this, root));
	}

	private void parseUniverse(BDSCompound compound) {
		BDSString nameBDS = compound.getString("Name", 0);
		this.name = (nameBDS != null ? nameBDS.getData() : "Universe " + root.getName());
		this.customData = compound.getComp("CustomData", 0);
	}

	public void render(Graphics g) {
		if (this.currentWorld != null) this.currentWorld.render(g);
	}

	public static List<Universe> getUniverseList(File path) {
		List<Universe> ret = new ArrayList<>();

		for (File folder : path.listFiles(onlyFolders)) {

			File f = new File(folder, "universe.dat");
			if (!f.exists()) continue;
			BDSCompound data;
			try {
				data = new BDSCompound(f);
			} catch (Exception e) {
				continue;
			}
			ret.add(new Universe(data, folder));
		}
		return ret;
	}
}