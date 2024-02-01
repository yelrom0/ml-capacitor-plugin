export interface CapTorchPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
