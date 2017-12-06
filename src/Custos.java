
public class Custos {
	private int valor;
	private int qtdR;
	private boolean riscado;
	
	public Custos(int valor)
	{
		this.valor = valor;
		this.qtdR = 0;
		this.riscado = false;
	}
	public boolean isRiscado()
	{
		return riscado;
	}
	public void Riscar()
	{
		this.riscado = true;
		qtdR++;
	}
	public void Desriscar()
	{
		this.riscado = false;
	}
	public int getQtdr(){
		return qtdR;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor)
	{
		this.valor = valor;
	}
}
