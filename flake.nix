{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
  };

  outputs =
    {
      self,
      nixpkgs,
    }:
    let
      system = "x86_64-linux";
      pkgs = nixpkgs.legacyPackages.${system};
    in
    {
      devShells.${system}.default = pkgs.mkShell {
        nativeBuildInputs = with pkgs; [
          jdk25
        ];

        buildInputs = with pkgs; [
          libGL
          mesa
          glfw
          libx11
          libxext
          libxrandr
          libxcursor
          libxi
          libxinerama
        ];

        LD_LIbRARY_PATH = pkgs.lib.makeLibraryPath (
          with pkgs;
          [
            libGL
            mesa
            glfw
            libx11
            libxext
            libxrandr
            libxcursor
            libxi
            libxinerama
          ]
        );
      };
    };
}
