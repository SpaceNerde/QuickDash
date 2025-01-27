use eframe::egui;

fn main() {
    let native_options = eframe::NativeOptions::default();
    drop(eframe::run_native("Quick Dash", native_options, Box::new(|cc| Ok(
        Box::new(QuickDash::new(cc))
    ))));
}

#[derive(Default)]
struct QuickDash {}

impl QuickDash {
    fn new(_cc: &eframe::CreationContext<'_>) -> Self {
        Self::default()
    }
}

impl eframe::App for QuickDash {
    fn update(&mut self, ctx: &egui::Context, _frame: &mut eframe::Frame) {
        egui::CentralPanel::default().show(ctx, |ui| {
            ui.heading("Test");
        });
    }
}
