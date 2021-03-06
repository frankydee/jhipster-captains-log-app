/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CaptainsLogAppTestModule } from '../../../test.module';
import { LogEntryDeleteDialogComponent } from 'app/entities/log-entry/log-entry-delete-dialog.component';
import { LogEntryService } from 'app/entities/log-entry/log-entry.service';

describe('Component Tests', () => {
    describe('LogEntry Management Delete Component', () => {
        let comp: LogEntryDeleteDialogComponent;
        let fixture: ComponentFixture<LogEntryDeleteDialogComponent>;
        let service: LogEntryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaptainsLogAppTestModule],
                declarations: [LogEntryDeleteDialogComponent]
            })
                .overrideTemplate(LogEntryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LogEntryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LogEntryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
