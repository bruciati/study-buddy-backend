{ pkgs ? import <nixpkgs> {} }:

let
  jdk = pkgs.jdk17_headless;
in
pkgs.mkShell {
  buildInputs = with pkgs; [
    jdk
    jetbrains.idea-ultimate
  ];
}
