PER_OF_IMM = new double[LL]

for (int i = 0; i < LL; i++) {
    if (TOTAL_POP[i] != 0) {
        PER_OF_IMM[i] = (IMM[i] / TOTAL_POP[i]) * 100
    } else {
        PER_OF_IMM[i] = 0
    }
}
