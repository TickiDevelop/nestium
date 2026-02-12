package net.caffeinemc.mods.nestium.client.render.chunk.translucent_sorting.data;

class StaticSorter extends Sorter {
    StaticSorter(int quadCount) {
        this.initBufferWithQuadLength(quadCount);
    }

    @Override
    public void writeIndexBuffer(CombinedCameraPos cameraPos, boolean initial) {
        // no-op
    }
}
