export interface CapTorchPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  loadImage(options: { imagePath: string}): Promise<void>;
}
