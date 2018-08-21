/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CaptainsLogAppTestModule } from '../../../test.module';
import { ELRDeleteDialogComponent } from 'app/entities/elr/elr-delete-dialog.component';
import { ELRService } from 'app/entities/elr/elr.service';

describe('Component Tests', () => {
    describe('ELR Management Delete Component', () => {
        let comp: ELRDeleteDialogComponent;
        let fixture: ComponentFixture<ELRDeleteDialogComponent>;
        let service: ELRService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaptainsLogAppTestModule],
                declarations: [ELRDeleteDialogComponent]
            })
                .overrideTemplate(ELRDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ELRDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ELRService);
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
