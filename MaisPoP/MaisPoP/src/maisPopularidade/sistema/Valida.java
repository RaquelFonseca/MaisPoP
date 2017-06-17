package maisPopularidade.sistema;

import java.io.Serializable;

public class Valida implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2239209561099382486L;

	public Valida() {
		
	}

	
	public boolean nome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public boolean email(String email) {
		if (nome(email) & email.contains("@") & email.indexOf("@") != 0) {
			if (email.endsWith(".com") || email.endsWith(".com.br")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean senha(String senha) {
		if (senha == null || senha.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	private boolean dia(String dataNasc) {
		String dia = dataNasc.substring(0, 2);
		 for (char letra : dia.toCharArray())  
	            if(letra < '0' || letra > '9')  
	                return false;  
	        return true;  
	}
	
	private boolean mes(String dataNasc) {
		String mes = dataNasc.substring(3, 5);
		 for (char letra : mes.toCharArray())  
	            if(letra < '0' || letra > '9')  
	                return false;  
	        return true;  
	}
	
	private boolean ano(String dataNasc) {
		String ano = dataNasc.substring(6, 10);
		 for (char letra : ano.toCharArray())  
	            if(letra < '0' || letra > '9')  
	                return false;  
	        return true;  
	}
	
	public boolean formatoDataNasc(String dataNasc) {
		if (nome(dataNasc) & dataNasc.length() == 10) {
			for (int i = 0; i < dataNasc.length() - 2; i = i + 2) {
				if (dataNasc.indexOf("/") == i & dia(dataNasc)& mes(dataNasc)& ano(dataNasc)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean valorDataNasc(String dataNasc) {
		if (formatoDataNasc(dataNasc)) {
			String dia = dataNasc.substring(0, 2);
			String mes = dataNasc.substring(3, 5);
			String ano = dataNasc.substring(6, 10);
			if (Integer.parseInt(ano) < 2016) {
				if (mes.equals("01") || mes.equals("03") || mes.equals("05")
						|| mes.equals("06") || mes.equals("08")
						|| mes.equals("10") || mes.equals("12")) {
					if (Integer.parseInt(dia) <= 31 & Integer.parseInt(dia) > 0) {
						return true;
					} else
						return false;
				} else if (mes.equals("04") || mes.equals("07")
						|| mes.equals("09") || mes.equals("11")) {
					if (Integer.parseInt(dia) <= 30 & Integer.parseInt(dia) > 0) {
						return true;
					} else
						return false;
				} else if (mes.equals("02")) {
					boolean bissexto = ((Integer.parseInt(ano) % 400 == 0) || (Integer
							.parseInt(ano) % 4 == 0 && Integer.parseInt(ano) % 100 != 0));
					if (bissexto) {
						if (Integer.parseInt(dia) <= 29
								& Integer.parseInt(dia) > 0) {
							return true;
						} else
							return false;
					} else if (!bissexto) {
						if (Integer.parseInt(dia) <= 28
								& Integer.parseInt(dia) > 0) {
							return true;
						} else
							return false;
					}
				}
			}
		}
		return false;
	}

	public boolean conteudoPost(String mensagem) {
		if (mensagem == null || mensagem.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public String hashtags(String mensagem) {
		String[] palavras = mensagem.substring(mensagem.indexOf("#"),mensagem.length()).split(" ");
		for (String palavra : palavras) {
			if (!palavra.startsWith("#"))
				return palavra;
		}
		return null;
	}
	
}
