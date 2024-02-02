export interface CapTorchPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  loadImage(): Promise<{imageStr: string}>;
}
