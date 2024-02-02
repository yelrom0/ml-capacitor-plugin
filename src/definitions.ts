export interface CapTorchPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  loadImage(): Promise<{image: ImageData}>;
}

export interface ImageData {
  name: string;
  data: string;
  mimeType: string;
}