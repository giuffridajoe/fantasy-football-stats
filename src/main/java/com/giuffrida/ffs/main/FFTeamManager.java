package com.giuffrida.ffs.main;

public enum FFTeamManager {
	Bob("{CA683308-66E8-4172-9891-D8C2A8A09FE4}"),
	Steve("{797A0B2F-9B4F-4231-9264-5EAB154EEC2C}"),
	JoeG("{FED936E0-0A6F-43DB-87B5-ABE7B0A65799}"),
	JoeV("{66B8630F-0CBE-4B4E-BFD5-192B09C489DE}"),
	Shannon("{6B60B179-6FD4-44EA-BEEB-5452F6B1180B}"),
	Vaccaro("{3963B88C-28F7-45C1-8781-FB276E18AA17}"),
	Eric("{1B97B44B-B739-4670-8518-895EB6B80B36}"),
	Sean("{C6F97B7E-6150-4797-9CB5-F932C1958463}"),
	Ed("{1892C10F-CD14-4EAF-A701-47870982F254}"),
	Little("{A31A37BD-0B9F-4C8C-B6E2-5878858F9C3D}");

	private final String manager;
	FFTeamManager(String guid) {
		this.manager = guid;
	}

	public String toString() {
		return manager;
	}
	
	public static FFTeamManager getManagerFromGuid(String guid) throws Exception {
		switch (guid) {
			case "{CA683308-66E8-4172-9891-D8C2A8A09FE4}":
				return FFTeamManager.Bob;
			case "{797A0B2F-9B4F-4231-9264-5EAB154EEC2C}":
				return FFTeamManager.Steve;
			case "{FED936E0-0A6F-43DB-87B5-ABE7B0A65799}":
				return FFTeamManager.JoeG;
			case "{66B8630F-0CBE-4B4E-BFD5-192B09C489DE}":
				return FFTeamManager.JoeV;
			case "{6B60B179-6FD4-44EA-BEEB-5452F6B1180B}":
				return FFTeamManager.Shannon;
			case "{3963B88C-28F7-45C1-8781-FB276E18AA17}":
				return FFTeamManager.Vaccaro;
			case "{1B97B44B-B739-4670-8518-895EB6B80B36}":
				return FFTeamManager.Eric;
			case "{C6F97B7E-6150-4797-9CB5-F932C1958463}":
				return FFTeamManager.Sean;
			case "{1892C10F-CD14-4EAF-A701-47870982F254}":
				return FFTeamManager.Ed;
			case "{A31A37BD-0B9F-4C8C-B6E2-5878858F9C3D}":
				return FFTeamManager.Little;
			default: {
				throw new Exception("unrecognized guid for player");
			}
		}
	}
}
