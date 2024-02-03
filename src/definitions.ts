export interface ImageAIData {
  className: string;
  confidence: number;
}

export interface ImageData {
  name: string;
  data: string;
  mimeType: string;
}

export type ImageResponseType = 'image' | 'ai';

export interface ImageResponse {
  id: string;
  type: ImageResponseType;
  data: ImageData | ImageAIData;
}

export type CallbackID = string;

export type ImageResponseCallback = (response: ImageResponse) => void;
export interface CapTorchPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  loadImage(callback: ImageResponseCallback): Promise<CallbackID>;
}