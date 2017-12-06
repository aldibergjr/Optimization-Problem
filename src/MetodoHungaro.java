import java.util.*;
public class MetodoHungaro {
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("qual ordem da sua matriz?");
		int x = in.nextInt();
		Custos[][] custo = new Custos[x][x];
		
		for(int i = 0;i<x;i++) {
			for (int j = 0; j < custo.length; j++) {
				System.out.println("insira o elemento C"+(i+1)+""+(j+1)+" da matriz");
				
				custo[i][j] = new Custos(in.nextInt());
			}
		}
		Custos[][] aux = custo;
		aux = passoUm(aux);
		aux = passoDois(aux);
		aux = passoTei(aux);
		Dupla[] dupla = posOtima(aux);
		
		for (int i = 0; i < custo.length; i++) {
			System.out.println();
			for (int j = 0; j < custo.length; j++) {
				System.out.print(aux[i][j].getValor()+" ");
			}
		}
		
		for (int i = 0; i < dupla.length; i++) {
			System.out.println("Melhores posições: "+dupla[i].i+", "+dupla[i].j);
		}
		
			
	}
	public static Custos[][] passoUm(Custos[][] custo) {
		int lesser[]=new int[custo.length];
		for (int i = 0; i < custo.length; i++) {
			lesser[i] = custo[i][0].getValor();
			for (int j = 0; j < custo.length; j++) {
				
				if(custo[i][j].getValor() < lesser[i]) {
					lesser[i] = custo[i][j].getValor();
				}	
			}
			for (int j = 0; j < custo.length; j++) {
				custo[i][j].setValor(custo[i][j].getValor()-lesser[i]);
			}
		}
		return custo;
	}
	public static Custos[][] passoDois(Custos[][] custo)
	{
		int lesser[]=new int[custo.length];
		for (int i = 0; i < custo.length; i++) {
			lesser[i] = custo[0][i].getValor();
			for (int j = 0; j < custo.length; j++) {
				
				if(custo[j][i].getValor() < lesser[i]) {
					lesser[i] = custo[j][i].getValor();
				}	
			}
			for (int j = 0; j < custo.length; j++) {
				custo[j][i].setValor(custo[j][i].getValor()-lesser[i]);
			}
		}
		return custo;
	}
	
	
	public static Custos[][] passoTei(Custos[][] custo){
		int nriscos = 0;
		for (int i = 0; i < custo.length; i++) {
			for (int j = 0; j < custo.length; j++) {
				if(custo[i][j].getValor() == 0&& !custo[i][j].isRiscado()) {
					custo = findzeros(custo,i,j);
					nriscos++;
				}
				
			}
		}
		System.out.println(nriscos);
		return testeOtm(custo,nriscos);
	}
	
	 
	public static Custos[][] findzeros(Custos[][] custo, int linha,int coluna) {
		boolean isHor = true;
		int zeronumh = 0;
		int zeronumv = 0;
		for (int i = 0; i < custo.length; i++) {
			if(custo[linha][i].getValor() == 0 && !custo[linha][i].isRiscado()) {
				zeronumh++;
				
			}
		}
		for (int i = 0; i < custo.length; i++) {
			if(custo[i][coluna].getValor() == 0 && !custo[i][coluna].isRiscado()) {
				zeronumv++;
			}
		}
		if(zeronumh >= zeronumv)
		{
			for (int i = 0; i < custo.length; i++) {
				custo[linha][i].Riscar();
			}
		}else {
			for (int i = 0; i < custo.length; i++) {
				custo[i][coluna].Riscar();
			}
		}
		return custo;
	}
	
	public static Custos[][] testeOtm(Custos[][] custo,int nriscos){
		if(nriscos == custo.length)
		{
			return custo;
		}else 
		{
		int menor=100000;	
		 for (int i = 0; i < custo.length; i++) {
			for (int j = 0; j < custo.length; j++) {
				if (!custo[i][j].isRiscado()) {
					if(custo[i][j].getValor()<menor) {
						menor=custo[i][j].getValor();
					}
				}
			}
		}
		 //não é otimo
		 for (int i = 0; i < custo.length; i++) {
			for (int j = 0; j < custo.length; j++) {
				if(!custo[i][j].isRiscado()) {
					custo[i][j].setValor((custo[i][j].getValor())-menor);
				}
				if (custo[i][j].getQtdr()==2) {
					custo[i][j].setValor(custo[i][j].getValor()+menor);
				}
			}
		}
		 for (int i = 0; i < custo.length; i++) {
		for (int j = 0; j < custo.length; j++) {
			custo[i][j].Desriscar();
		}	
		}
			return passoTei(custo);
		}
		
		
	}
	
	public static Dupla[] posOtima(Custos[][] custo) {
		Dupla[] pos = new Dupla[custo.length];
		int linhazeros[] = new int[custo.length];
		
		for (int i = 0; i < pos.length; i++) {
			int zeros = 0;
			for (int j = 0; j < pos.length; j++) {
				custo[i][j].Desriscar();
				if(custo[i][j].getValor() == 0)
				{
					zeros++;
				}
			}
			linhazeros[i] = zeros;
		}
		for(int j = 0;j<custo.length;j++)
		{
		int melhorln = melhorln(linhazeros);
		System.out.println(melhorln);
		for (int i = 0; i < custo.length; i++) {
			if(custo[melhorln][i].getValor()==0 && !custo[melhorln][i].isRiscado()) {
				linhazeros[melhorln] = linhazeros.length+2;
				custo = riscarColuna(custo,i);
				Dupla dupla = new Dupla(melhorln+1,i+1);
				System.out.println(" done");
				pos[j] = dupla;
				break;
			}
		}
		// System.out.println(custo[1][0].isRiscado());
		
		}
		return pos;
	}
	public static Custos[][] riscarColuna(Custos[][] custo,int coluna)
	{
		for (int i = 0; i < custo.length; i++) {
			custo[i][coluna].Riscar();
		}
		return custo;
	}
	public static int melhorln(int[] zeros) {
		int melhorln = 0;
		int menor = zeros.length+2;
		for (int i = 0; i < zeros.length; i++) {
			if(zeros[i]<menor)
			{
				menor = zeros[i];
				melhorln = i;
			}
		}
		return melhorln;
	}
	
}
