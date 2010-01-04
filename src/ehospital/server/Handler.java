package ehospital.server;

public class Handler {
	protected CryptoManager cm;
	protected DBManager dbm;
	
	public Handler() {
		cm = new CryptoManager();
		dbm = new DBManager();
	}
	
	public String byteArrayToString(byte[] b) {
		String HEX_NUM = "0123456789abcdef";
		char[] cStr = new char[b.length*2];
		for(int i = 0, j = 0; i< b.length; i++) {
			byte f = (byte)((b[i] & 0xF0) >> 4);
			byte g = (byte) (b[i] & 0x0F);
			cStr[j] =  HEX_NUM.charAt(f);
			j++;
			cStr[j] = HEX_NUM.charAt(g); 
			j++;
		}
		return new String(cStr);
	}
	
	public byte[] stringToByteArray(String str) {
		String HEX_NUM = "0123456789abcdef";
		int CHAR_NOT_FOUND = -1;
		byte[] b = new byte[str.length()/2];
		for(int i = 0, j = 0; i< str.length(); i++) {
			byte f = (byte)HEX_NUM.indexOf(str.charAt(j));
			if (f != CHAR_NOT_FOUND) {
				b[i] = (byte)(f << 4);
				j++;
				byte g = (byte)HEX_NUM.indexOf(str.charAt(j));
				if (g != CHAR_NOT_FOUND) {
					b[i] = (byte) (b[i] + g);
					j++;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}	
		return b;
	}
}
