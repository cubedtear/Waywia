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

package aritzh.waywia.blocks;

import aritzh.waywia.bds.BDSCompound;
import aritzh.waywia.bds.BDSInt;
import aritzh.waywia.bds.BDSStorable;
import com.google.common.collect.Lists;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.List;

/**
 * @author Aritz Lopez
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class Block implements BDSStorable {

	private static final List<Block> blocks = Lists.newArrayList();

	public static final int SIZE = 16;

	public static int registerBlock(Block block) {
		if (Block.blocks.contains(block))
			throw new IllegalArgumentException("Block " + block + " was already registered");
		if (Block.blocks.get(block.getID()) != null)
			throw new IllegalArgumentException("Block id " + block.getID() + "is occupied by " + Block.blocks.get(block.getID()) + " when adding " + block);
		Block.blocks.set(block.getID(), block);

		return Block.blocks.indexOf(block);
	}

	public static Block getBlock(int id) {
		return Block.blocks.get(id);
	}

	public static Block fromBDS(BDSCompound comp) {
		int id = comp.getInt("ID", 0).getData();
		return Block.getBlock(id);
	}

	public void render(int x, int y, Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.blue);
		g.fillRect(x * Block.SIZE, y * Block.SIZE, Block.SIZE, Block.SIZE);
		g.setColor(Color.white);
		g.drawRect(x * Block.SIZE, y * Block.SIZE, Block.SIZE, Block.SIZE);
		g.setColor(c);
	}

	public abstract String getName();

	public BDSCompound toBDS() {
		return new BDSCompound("Block").add(new BDSInt(this.getID(), "ID"));
	}

	public void clicked(int x, int y) {
	}

	public void update(int x, int y, int delta) {
	}

	public abstract int getID();
}
